package com.lenovo.smarttraffic.bean;

import java.util.List;

public class RoadLatlng {

    private RouteBean route;
    private String count;
    private String RESULT;
    private String ERRMSG;

    public RouteBean getRoute() {
        return route;
    }

    public void setRoute(RouteBean route) {
        this.route = route;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getERRMSG() {
        return ERRMSG;
    }

    public void setERRMSG(String ERRMSG) {
        this.ERRMSG = ERRMSG;
    }

    public static class RouteBean {
        private String origin;
        private String destination;
        private List<PathsBean> paths;

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public List<PathsBean> getPaths() {
            return paths;
        }

        public void setPaths(List<PathsBean> paths) {
            this.paths = paths;
        }

        public static class PathsBean {

            private String distance;
            private String duration;
            private String strategy;
            private String tolls;
            private String tollDistance;
            private String restriction;
            private String trafficLights;
            private List<StepsBean> steps;

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getStrategy() {
                return strategy;
            }

            public void setStrategy(String strategy) {
                this.strategy = strategy;
            }

            public String getTolls() {
                return tolls;
            }

            public void setTolls(String tolls) {
                this.tolls = tolls;
            }

            public String getTollDistance() {
                return tollDistance;
            }

            public void setTollDistance(String tollDistance) {
                this.tollDistance = tollDistance;
            }

            public String getRestriction() {
                return restriction;
            }

            public void setRestriction(String restriction) {
                this.restriction = restriction;
            }

            public String getTrafficLights() {
                return trafficLights;
            }

            public void setTrafficLights(String trafficLights) {
                this.trafficLights = trafficLights;
            }

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            public static class StepsBean {

                private String instruction;
                private String orientation;
                private String distance;
                private String tolls;
                private String tollDistance;
                private String duration;
                private String polyline;
                private String road;

                public String getInstruction() {
                    return instruction;
                }

                public void setInstruction(String instruction) {
                    this.instruction = instruction;
                }

                public String getOrientation() {
                    return orientation;
                }

                public void setOrientation(String orientation) {
                    this.orientation = orientation;
                }

                public String getDistance() {
                    return distance;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }

                public String getTolls() {
                    return tolls;
                }

                public void setTolls(String tolls) {
                    this.tolls = tolls;
                }

                public String getTollDistance() {
                    return tollDistance;
                }

                public void setTollDistance(String tollDistance) {
                    this.tollDistance = tollDistance;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public String getPolyline() {
                    return polyline;
                }

                public void setPolyline(String polyline) {
                    this.polyline = polyline;
                }


                public String getRoad() {
                    return road;
                }

                public void setRoad(String road) {
                    this.road = road;
                }
            }
        }
    }
}
